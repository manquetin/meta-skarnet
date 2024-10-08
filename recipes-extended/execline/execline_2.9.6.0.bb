SUMMARY = "A non-interactive scripting language"
DESCRIPTION = "execline is a (non-interactive) scripting language as powerful \
as a shell but with a far more logical and predictable syntax and with no \
security issues."
HOMEPAGE = "https://skarnet.org/software/execline"
SECTION = "base"

LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=c92b5c6593e97d6cc9bcb4892128e2b8"

SRC_URI = "https://skarnet.org/software/${BPN}/${BPN}-${PV}.tar.gz"
SRC_URI[sha256sum] = "ba2a27e97c5eb6bd7ca6a0987a8925e44465a5be996daa0d18f8feca37d7571a"

PACKAGES =+ "libexecline"

inherit skarnet

PACKAGECONFIG ??= "multicall"
PACKAGECONFIG:class-native = ""
PACKAGECONFIG:class-nativesdk = ""
PACKAGECONFIG[multicall] = "--enable-multicall,--disable-multicall"
PACKAGECONFIG[pedantic-posix] = "--enable-pedantic-posix,--disable-pedantic-posix"

do_install:append () {
    # Prepare to replace symlinks with alternatives for multicall binary
    if ${@bb.utils.contains('PACKAGECONFIG', 'multicall', 'true', 'false', d)}; then
        install -d -m 0755 "${D}${sysconfdir}"
        find "${D}${bindir}" -lname execline -print -delete \
            | sed -e "s:${D}::" \
            | sort -u \
            > "${D}${sysconfdir}/execline.links"
    fi
}

FILES:libexecline = "${libdir}/lib*${SOLIBS}"

inherit update-alternatives

python do_package:prepend () {
    # Generate ALTERNATIVE* variables
    dvar = d.getVar('D')
    pn = d.getVar('PN')

    links = d.expand('${sysconfdir}/execline.links')
    target = d.expand('${bindir}/execline')

    if os.path.exists('%s%s' % (dvar, links)):
        with open('%s%s' % (dvar, links), 'r') as fd:
            for alt_link_name in fd:
                alt_link_name = alt_link_name.strip()
                alt_name = os.path.basename(alt_link_name)

                # Handle conflicts with some Yocto variable flags
                if alt_name == 'export':
                    alt_name = 'elexport'
                if alt_name == 'unexport':
                    alt_name = 'elunexport'

                d.appendVar('ALTERNATIVE:%s' % (pn), ' ' + alt_name)
                d.setVarFlag('ALTERNATIVE_LINK_NAME', alt_name, alt_link_name)
                if os.path.exists('%s%s' % (dvar, target)):
                    d.setVarFlag('ALTERNATIVE_TARGET', alt_name, target)
}

BBCLASSEXTEND = "native nativesdk"
