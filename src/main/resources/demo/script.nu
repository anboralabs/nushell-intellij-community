#!/usr/bin/env nu
# NuShell Demo Script

# Variables and types
let name = "world"
let count = 42
let pi = 3.14
let hex = 0xFF
let bin = 0b1010
let oct = 0o755
let big = 1_000_000
let sci = 6.022e23

# Booleans and null
let active = true
let empty = null

# Durations and filesizes
let timeout = 30sec
let delay = 100ms
let size = 5mb
let limit = 2gib

# String interpolation
let greeting = $"Hello ($name), count is ($count)"

# Control flow
if $count > 10 {
    print "big"
} else {
    print "small"
}

# Functions
def greet [name: string] {
    $"Hello ($name)!"
}

# Loops and ranges
for i in 1..10 {
    print $i
}

# Match
match $count {
    0 => "zero"
    _ => "other"
}

# Pipelines and operators
ls | where size > 1mb | sort-by name

# Flags
ls --long -a

# Module keywords
export def my-command [] { }

# Error handling
try {
    error make { msg: "oops" }
} catch { |e|
    print $e.msg
}

# Records and lists
let record = {name: "Alice", age: 30}
let list = [1, 2, 3]
let table = [[name age]; ["Alice" 30] ["Bob" 25]]

# Operators
let result = 10 + 5 * 2
let check = $count == 42 and $active
let bits = 0xFF bit-and 0x0F
