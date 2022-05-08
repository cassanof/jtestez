//go:build windows
// +build windows

package lib

import (
	"io"
	"log"
	"os"
	"os/exec"
)

// runs jshell on the files
func (ctx SourceContext) jshell() {
	classpath := tryGetClasspath()

	args := []string{"--class-path", classpath + ctx.Dir + "/lombok.jar"}
	args = append(args, ctx.getAllFilePaths(ctx.Dir+"/delomboked")...)
	// create process
	jshell := exec.Command("jshell", args...)

	// get pipe to stdin
	stdin, err := jshell.StdinPipe()
	if err != nil {
		log.Fatal(err)
	}
	defer stdin.Close()

	// redirect stdout and stderr
	jshell.Stdout = os.Stdout
	jshell.Stderr = os.Stderr

	// concurrently start jshell
	err = jshell.Start()
	if err != nil {
		log.Fatal(err)
	}

	// SETTINGS:
	// /set mode jtestez normal -command
	// /set truncation jtestez 100000000
	// /set feedback jtestez

	io.WriteString(stdin, "/set mode jtestez normal -command\n")
	io.WriteString(stdin, "/set truncation jtestez 100000000\n")
	io.WriteString(stdin, "/set feedback jtestez\n")

	var b []byte = make([]byte, 1)
	for {
		os.Stdin.Read(b)
		io.WriteString(stdin, string(b))
	}
}
