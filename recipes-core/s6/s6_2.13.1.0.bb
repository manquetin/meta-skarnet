SUMMARY = "skarnet.org's small and secure supervision suite"
DESCRIPTION = "s6 is a small supervision suite of programs for UNIX, designed \
to allow process supervision, in the line of daemontools and runit."
HOMEPAGE = "https://skarnet.org/software/s6"
SECTION = "base"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=c92b5c6593e97d6cc9bcb4892128e2b8"

SRC_URI = "https://skarnet.org/software/${BPN}/${BPN}-${PV}.tar.gz"
SRC_URI[sha256sum] = "bf0614cf52957cb0af04c7b02d10ebd6c5e023c9d46335cbf75484eed3e2ce7e"

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
