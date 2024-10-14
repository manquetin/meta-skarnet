SUMMARY = "A secure NSS-like implementation"
DESCRIPTION = "nsss is a secure implementation of a name service switch \
providing getpwnam() et al. functionality by communicating over a UNIX socket \
with a daemon."
HOMEPAGE = "https://skarnet.org/software/nsss"
SECTION = "base"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=97a21eece1f23a77df40063449656bcc"

SRC_URI = "https://skarnet.org/software/${BPN}/${BPN}-${PV}.tar.gz"
SRC_URI[sha256sum] = "954c1b25791cc36d07c3e123ec03436d3e296bf5233f1d08bc016d6d7e6279d2"

PACKAGES =+ "nsssd libnsss libnsssd"

inherit skarnet

FILES:nsssd = "${bindir}/nsssd*"
FILES:libnsss = "${libdir}/libnsss${SOLIBS}"
FILES:libnsssd = "${libdir}/libnsssd${SOLIBS}"

RRECOMMENDS:nsssd += "s6"
