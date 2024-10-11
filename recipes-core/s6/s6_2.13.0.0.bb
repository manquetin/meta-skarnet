SUMMARY = "skarnet.org's small and secure supervision suite"
DESCRIPTION = "s6 is a small supervision suite of programs for UNIX, designed \
to allow process supervision, in the line of daemontools and runit."
HOMEPAGE = "https://skarnet.org/software/s6"
SECTION = "base"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=c92b5c6593e97d6cc9bcb4892128e2b8"

SRC_URI = "https://skarnet.org/software/${BPN}/${BPN}-${PV}.tar.gz"
SRC_URI[sha256sum] = "7e46f8f55d80bb0e2025a64d5d649af4a4ac21e348020caaadde30ba5e5b4830"

PACKAGES =+ "libs6"

inherit skarnet

PACKAGECONFIG ??= "execline"
PACKAGECONFIG[execline] = "--enable-execline,--disable-execline,execline"
PACKAGECONFIG[nsss] = "\
    --enable-nsss --with-include=${STAGING_INCDIR}/nsss, \
    --disable-nsss,\
    nsss \
"

FILES:libs6 = "${libdir}/lib*${SOLIBS}"

BBCLASSEXTEND = "native nativesdk"
