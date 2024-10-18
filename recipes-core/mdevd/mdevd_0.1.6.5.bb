SUMMARY = "A mdev-compatible Linux hotplug manager daemon"
DESCRIPTION = "mdevd is a small daemon managing kernel hotplug events, \
similarly to udevd. The point of mdevd is to provide a drop-in replacement \
to busybox's mdev that does not fork while using the same configuration file."
HOMEPAGE = "https://skarnet.org/software/mdevd"
SECTION = "base"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=7eeaa4e68fa0d5966d994e8083b29f44"

SRC_URI = "https://skarnet.org/software/mdevd/mdevd-${PV}.tar.gz"
SRC_URI[sha256sum] = "d5c86420c81b2e4720b41e4f48f727cd5d9a17f4e6317115d21c32099e72a628"

PROVIDES = "udev"

inherit skarnet

PACKAGECONFIG ??= ""
PACKAGECONFIG[execline] = ",,,execline"
PACKAGECONFIG[nsss] = "\
    --enable-nsss --with-include=${STAGING_INCDIR}/nsss, \
    --disable-nsss,\
    nsss \
"

RPROVIDES:${PN} = "hotplug udev"
