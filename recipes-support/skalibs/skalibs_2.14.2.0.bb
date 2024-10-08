SUMMARY = "Base library for all software at skarnet.org"
DESCRIPTION = "skalibs is a package centralizing the free software / open \
source C development files used for building all software at skarnet.org: it \
contains essentially general-purpose libraries. skalibs can also be used as a \
sound basic start for C development. There are a lot of general-purpose \
libraries out there; but if your main goal is to produce small and secure C \
code with a focus on system programming, skalibs might be for you."
HOMEPAGE = "https://skarnet.org/software/skalibs"
SECTION = "libs"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=c92b5c6593e97d6cc9bcb4892128e2b8"

SRC_URI = "https://skarnet.org/software/${BPN}/${BPN}-${PV}.tar.gz"
SRC_URI[sha256sum] = "ddfec5730e5b2f19d0381ecf7f796b39a6e473236bda0ad8d3776a3fe7b07e43"

inherit skarnet

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)}"
PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6"
PACKAGECONFIG[iopause-select] = "--enable-iopause-select,--disable-iopause-select"
PACKAGECONFIG[tai] = "--enable-tai-clock,--disable-tai-clock"

SYSDEPS_CONFARGS = " \
    --with-sysdep-devurandom=yes \
    --with-sysdep-posixspawnearlyreturn=no \
"
SYSDEPS_CONFARGS:class-nativesdk = " \
    --with-sysdep-devurandom=yes \
    --with-sysdep-posixspawnearlyreturn=no \
"
SYSDEPS_CONFARGS:class-native = ""

EXTRA_OECONF:append = " ${SYSDEPS_CONFARGS}"

FILES:${PN}-dev += "${libdir}/skalibs/sysdeps"

BBCLASSEXTEND = "native nativesdk"
