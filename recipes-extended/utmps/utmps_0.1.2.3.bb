SUMMARY = "A secure utmp implementation"
DESCRIPTION = "utmps is a secure implementation of user accounting using a \
daemon as the only authority to manage utmp/wtmp data."
HOMEPAGE = "https://skarnet.org/software/utmps"
SECTION = "base"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=97a21eece1f23a77df40063449656bcc"

SRC_URI = "https://skarnet.org/software/utmps/utmps-${PV}.tar.gz"
SRC_URI[sha256sum] = "318ac799ed17c3fbf4281085b4b071facbd35c29852a5c643c24fa2869fc0545"

PACKAGES =+ "utmpd libutmps"

inherit skarnet

PACKAGECONFIG ??= ""
PACKAGECONFIG[nsss] = "\
    --enable-nsss --with-include=${STAGING_INCDIR}/nsss, \
    --disable-nsss,\
    nsss \
"

FILES:utmpd = "${bindir}/utmps-*d"
FILES:libutmps = "${libdir}/libutmps${SOLIBS}"

RRECOMMENDS:utmpd += "s6"
