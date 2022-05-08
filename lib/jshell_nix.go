//go:build darwin || dragonfly || freebsd || linux || nacl || netbsd || openbsd || solaris
// +build darwin dragonfly freebsd linux nacl netbsd openbsd solaris

package lib

import (
	"log"
	"os/exec"
	"syscall"
)

// runs jshell on the files
func (ctx SourceContext) jshell() {
	classpath := tryGetClasspath()

	jshellPath, err := exec.LookPath("jshell")
	if err != nil {
		log.Fatal(err)
	}

	syscall.Exec(jshellPath, ctx.getAllFilePaths(ctx.Dir+"/delomboked"), []string{"CLASSPATH=" + classpath + ":" + ctx.Dir + "/lombok.jar"})

	// SETTINGS:
	// /set mode jtestez normal -command
	// /set truncation jtestez 100000000
	// /set feedback jtestez
}
