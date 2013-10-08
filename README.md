# NetLogo Structs Extension

* [Quickstart](#quickstart)
* [What is it?](#what-is-it)
* [Installation](#installation)
* [Examples](#examples)
* [Behavior](#behavior)
* [Primitives](#primitives)
* [Building](#building)
* [Authors](#authors)
* [Feedback](#feedback-bugs-feature-requests)
* [Credits](#credits)
* [Terms of use](#terms-of-use)

## Quickstart

[Install the structs extension](#installation)

Include the extension in your NetLogo model (at the top):

    extensions [structs]

**Stacks**

Create a global varaible to store the stack and add some data to the stack in the setup procedure:

    globals[stack]
    to setup
      set stack structs:new-stack

      stackucts:stack-push stack 1
      stackucts:stack-push stack 2
      stackucts:stack-push stack "hello world"
      stackucts:stack-push stack [5 6 7]

    end

Report the size of the stack on the command line:

    show structs:stack-count stack

Does your stack contain 2?

    show structs:stack-contains stack 2

Pop the data from the stack, assigning the returned values to variables (note last in, first out):

    to pop
      let a structs:stack-pop str
      let b structs:stack-pop str
      let c structs:stack-pop str
      let d structs:stack-pop str

      print a
      print b
      print c
      print d
    end

[back to top](#netlogo-structs-extension)

## What is it?

This package contains the NetLogo **structs extension**, which provides NetLogo with a variety of common java data structures that are useful programming tools. 

**Stacks**

A stack is like a list but the manner in which elements are added and removed from the list is unique.  To add elements, you "push" them onto the stack.  At any time you can then "pop" the elements from the stack which simultaneously returns the last element to be pushed and removes that element from the stack..  Like a stack of dishes, you push new elements onto the top of the stack and you pop elements off the top, so last in is first out.

This extension is powered by the [Joda Time API for Java](http://joda-time.sourceforge.net/), which has very sophisticated and comprehensive date/time facilities.  A subset of these capabilities have been extended to NetLogo.  The **time extension** makes it easy to convert string representations of dates and date/times to a **LogoTime** object which can then be used to do many common time manipulations such as incrementing the time by some amount (e.g. add 3.5 days to 2001-02-22 10:00 to get 2001-02-25 22:00).

[back to top](#netlogo-structs-extension)

## Installation

First, [download the latest version of the extension](https://github.com/colinsheppard/structs/releases). Note that the latest version of this extension was compiled against NetLogo 5.0.4; if you are using a different version of NetLogo you might consider building your own jar file ([see building section below](#building)).

Unzip the archive and move the directory to the "extensions" directory inside your NetLogo application folder (i.e. [NETLOGO]/extensions/).  Or you can place the directory under the same directory holding the NetLogo model in which you want to use this extension.

For more information on NetLogo extensions:
[http://ccl.northwestern.edu/netlogo/docs/extensions.html](http://ccl.northwestern.edu/netlogo/docs/extensions.html)

[back to top](#netlogo-structs-extension)

## Examples

See the example models in the extension subfolder "examples" for thorough demonstrations of usage.

## Data Types

The **structs extension** introduces some new data types (more detail about these is provided in the [behavior section](#behavior)):

* **LogoStack** - A LogoStack object stores a stack. 

[back to top](#netlogo-structs-extension)

## Behavior

Elements of logostacks can be any valid Netlogo object.  Like a list, stacks can contain a mixture of data types.

[back to top](#netlogo-structs-extension)

## Primitives

### Stacks

**structs:new-stack**

*structs:new-stack*

Reports a *logostack*.

    let stack structs:new-stack

---------------------------------------

**structs:stack-push**

*structs:stack-push logostack element*

Pushes *element* onto the *logostack*.

    structs:stack-push stack 99

---------------------------------------

**structs:stack-pop**

*structs:stack-pop logostack*

Reports the latest *element* to be pushed onto *logostack* and removes that element from the stack.

    structs:stack-push stack 99
    structs:stack-push stack 100

    print structs:stack-pop stack
    print structs:stack-pop stack

    ;; this will print "100" and then "99"

---------------------------------------

**structs:stack-peek**

*structs:stack-peek logostack*

Reports the lates *element* to be pushed onto *logostack* but does **not** remove that element from the stack.

    structs:stack-push stack 99
    structs:stack-push stack 100

    print structs:stack-peek stack
    print structs:stack-peek stack

    ;; this will print "100" and then "100"

---------------------------------------

**structs:stack-count**

*structs:stack-count logostack*

Reports the number of elements in the *logostack*.

---------------------------------------

**structs:stack-contains**

*structs:stack-contains logostack element*

Tests if *element* is contained in *logostack*, returning true or false.


[back to top](#netlogo-structs-extension)

## Building

Use the NETLOGO environment variable to tell the Makefile which NetLogoLite.jar to compile against.  For example:

    NETLOGO=/Applications/NetLogo\\\ 5.0 make

If compilation succeeds, `structs.jar` will be created.  See [Installation](#installation) for instructions on where to put your compiled extension.

## Author

Colin Sheppard

## Feedback? Bugs? Feature Requests?

Please visit the [github issue tracker](https://github.com/colinsheppard/structs/issues?state=open) to submit comments, bug reports, or feature requests.  I'm also more than willing to accept pull requests.

## Terms of Use

[![CC0](http://www.gnu.org/graphics/gplv3-127x51.png)](http://www.gnu.org/copyleft/gpl.html)

The NetLogo structs extension is under GPL v3.

[back to top](#netlogo-structs-extension)
