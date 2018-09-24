#!/usr/bin/env bash

# Add git info if it's present to PS1

export GIT_PS1_SHOWDIRTYSTATE=true
export GIT_PS1_SHOWSTASHSTATE=true
export GIT_PS1_SHOWUNTRACKEDFILES=true
export GIT_PS1_SHOWUPSTREAM=auto

case "$TERM" in
    xterm*|rxvt*)
        case "$TERM" in
            xterm|xterm-*color) PS1='${debian_chroot:+($debian_chroot)}\[\033[01;32m\]\u@\h\[\033[00m\]:\[\033[01;34m\]\w\a\[\033[01;33m\]$(__git_ps1)\[\033[00m\]\$ ';;
            *) PS1='${debian_chroot:+($debian_chroot)}\u@\h:\w\a$(__git_ps1)\$ ';;
        esac
    ;;
esac