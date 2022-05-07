package lib

import (
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
)

type SourceContext struct {
	DirPath     string
	TempdirPath string
	FileNames   []string
}

// generates the context by reading the java files in the given
// directory and creates the temporary directory.
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

	// get file names
	var filenames []string
	for _, v := range entries {
		filenames = append(filenames, v.Name())
	}

	// create temp dir
	dir, err := ioutil.TempDir("", "jtestez")
	if err != nil {
		log.Fatal(err)
	}

	return SourceContext{
		DirPath:     fullpath,
		TempdirPath: dir,
		FileNames:   filenames,
	}
}

func (s *SourceContext) getName() string {
	return s.DirPath
}
