SUMMARY = "A secure NSS-like implementation"
DESCRIPTION = "nsss is a secure implementation of a name service switch \
providing getpwnam() et al. functionality by communicating over a UNIX socket \
with a daemon."
HOMEPAGE = "https://skarnet.org/software/nsss"
SECTION = "base"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=3d51c36fc24946e3b9febf4e348aac97"

SRC_URI = "https://skarnet.org/software/${BPN}/${BPN}-${PV}.tar.gz"
SRC_URI[sha256sum] = "39b504f85bd8f6b523d334e5cfa62c02a395db35991b75f206df0abbd6761aad"

PACKAGES =+ "nsssd libnsss libnsssd"

inherit skarnet

FILES:nsssd = "${bindir}/nsssd*"
FILES:libnsss = "${libdir}/libnsss${SOLIBS}"
FILES:libnsssd = "${libdir}/libnsssd${SOLIBS}"

RRECOMMENDS:nsssd += "s6"

BBCLASSEXTEND = "native nativesdk"
