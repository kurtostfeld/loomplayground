.PHONY: clean
clean:
	rm -rf .idea build out target

.PHONY: build
build:
	mvn package

.PHONY: failfast
failfast: build
	java --enable-preview --module-path target/classes -m loomplayground/loomplayground.FailFast

.PHONY: firsttosucceed
firsttosucceed: build
	java --enable-preview --module-path target/classes -m loomplayground/loomplayground.FirstToSucceed
