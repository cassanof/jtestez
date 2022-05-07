package main

import (
	"fmt"
	"os"

	"github.com/elleven11/jtestez/lib"
)

func main() {
	a := lib.GenContext(os.Args[1])
	fmt.Printf("dirpath: %s\ntempdir: %s\nfiles: %v", a.DirPath, a.TempdirPath, a.FileNames)
	a.Run()
}
