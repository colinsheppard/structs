ifeq ($(origin JAVA_HOME), undefined)
  JAVA_HOME=/usr
endif

ifeq ($(origin NETLOGO), undefined)
  NETLOGO=../..
endif

ifneq (,$(findstring CYGWIN,$(shell uname -s)))
  COLON=\;
  JAVA_HOME := `cygpath -up "$(JAVA_HOME)"`
else
  COLON=:
endif

SRCS=$(wildcard src/*.java)

structs.jar structs.jar.pack.gz: $(SRCS) manifest.txt
	mkdir -p classes
	$(JAVA_HOME)/bin/javac -g -encoding us-ascii -source 1.5 -target 1.5 -classpath $(NETLOGO)/NetLogoLite.jar -d classes $(SRCS)
	jar cmf manifest.txt structs.jar -C classes .
	pack200 --modification-time=latest --effort=9 --strip-debug --no-keep-file-order --unknown-attribute=strip structs.jar.pack.gz structs.jar

structs.zip: structs.jar
	rm -rf structs
	mkdir structs
	cp -rp structs.jar structs.jar.pack.gz README.md Makefile src manifest.txt structs
	zip -rv structs structs
	rm -rf structs
