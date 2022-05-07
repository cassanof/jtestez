package lib

import (
	"bufio"
	_ "embed"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
	"regexp"
	"strings"
	"sync"
)

type SourceContext struct {
	DirPath     string
	TempdirPath string
	FileNames   []string
}

//go:embed assets/imports.txt
var importsData []byte

//go:embed assets/lombok.jar
var lombokJarData []byte

//go:embed assets/BetterToString.java
var btsData []byte

// generates the context by reading the java files in the given
// directory and creates the temporary directory with the files copied over.
func GenContext(fp string) SourceContext {
	entries, err := os.ReadDir(fp)
	if err != nil {
		// TODO: do better error handling
		log.Fatal(err)
	}

	// TODO: fix
	fullpath, err := filepath.Abs(fp)
	if err != nil {
		// TODO: do better error handling
		log.Fatal(err)
	}

	// create temp tmpdir
	tmpdir, err := ioutil.TempDir("", "jtestez")
	if err != nil {
		log.Fatal(err)
	}

	// get file names, and copy over files
	var filenames []string
	for _, entry := range entries {
		name := entry.Name()
		if strings.LastIndex(name, ".java") == (len(name) - 5) {
			tmppath := tmpdir + "/" + name
			realpath := fullpath + "/" + name

			input, err := ioutil.ReadFile(realpath)
			if err != nil {
				log.Fatal(err)
			}

			err = ioutil.WriteFile(tmppath, append(append(importsData, input...), btsData...), 0655)
			if err != nil {
				log.Fatal(err)
			}

			filenames = append(filenames, name)
		}
	}

	return SourceContext{
		DirPath:     fullpath,
		TempdirPath: tmpdir,
		FileNames:   filenames,
	}
}

func (s SourceContext) Run() {
	s.writeAnnot()
}

// writes the lombok annotations to each class in the files
func (s SourceContext) writeAnnot() {
	var wg sync.WaitGroup
	for _, name := range s.FileNames {
		wg.Add(1)
		// it makes it asynchronous
		go func(name string) {
			fpath := s.TempdirPath + "/" + name
			// read the file, split it into an array of lines
			file, err := os.OpenFile(fpath, os.O_RDWR, 0655)
			if err != nil {
				log.Fatal(err)
			}

			sc := bufio.NewScanner(file)
			lines := make([][]byte, 0)
			for sc.Scan() {
				lines = append(lines, []byte(sc.Text()))
			}

			// close file
			file.Close()

			// match regex on each line, get indexes of class lines
			indexes := make([]int, 0)
			for i, l := range lines {
				ok, err := regexp.Match("(class).*{", l)
				if err != nil {
					log.Fatal(err)
				}
				if ok {
					indexes = append(indexes, i)
				}
			}

			// remove the BetterToString class from indexes.
			indexes = indexes[:len(indexes)-1]

			// write @ToString annot to to each line before class
			for counter, i := range indexes {
				annot := []byte("@ToString")
				lines = append(lines[:i+1+counter], lines[i+counter:]...)
				lines[i+counter] = annot
			}

			// flatten buffer
			flattened := make([]byte, 0)
			for _, l := range lines {
				flattened = append(flattened, append(l, []byte("\n")...)...)
			}

			// reopen file
			file, err = os.OpenFile(fpath, os.O_RDWR|os.O_CREATE|os.O_TRUNC, 0655)
			if err != nil {
				log.Fatal(err)
			}

			defer file.Close()

			// write to file
			_, err = file.Write(flattened)
			if err != nil {
				log.Fatal(err)
			}

			wg.Done()
		}(name)
	}
	wg.Wait()
}
