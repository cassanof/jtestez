package lib

import "os"

// tries to retrieve a classpath environment variable.
func tryGetClasspath() string {
	// try lookup linux env
	env, ok := os.LookupEnv("CLASSPATH")
	if ok {
		return env
	}
	// try lookup windows env
	env, ok = os.LookupEnv("%CLASSPATH%")
	if ok {
		return env
	}
	return ""
}
