package main

import (
	"fmt"
	"os"

	"github.com/elleven11/jtestez/lib"
)

func main() {
	sources := lib.GenContext(os.Args[1:]...)
	fmt.Printf("tempdir: %s\nfiles: %v\n", sources.Dir, sources.Names)
	sources.Run()
}
