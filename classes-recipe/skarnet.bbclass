#
# Copyright (c) 2024 Mathieu Anquetin <mathieu@anquetin.eu>
#
# SPDX-License-Identifier: MIT

# Known mirrors (https://skarnet.org/mirrors.html)
SKARNET_MIRROR = "https://skarnet.org"
MIRRORS += "\
    ${SKARNET_MIRROR}   https://mirror.heliocat.net/skarnet.org \n \
    ${SKARNET_MIRROR}   https://mirror.stinpriza.org/skarnet.org \n \
    ${SKARNET_MIRROR}   https://skamirror.erminea.space \n \
    ${SKARNET_MIRROR}   https://static.bitcheese.net/skarnet.org \n \
"

# All software depend on skalibs (except skalibs itself)
DEPENDS:prepend = "${@oe.utils.conditional('BPN', 'skalibs', '', 'skalibs', d)} "

# Configure options
def get_sysdeps_opt(d):
    from re import search

    pn = d.getVar('PN')
    if search('skalibs', pn):
        sysdepdir = oe.path.join(d.getVar('libdir'), 'skalibs', 'sysdeps')
        opt = '--sysdepdir=' + sysdepdir
    else:
        staging_libdir = d.getVar('STAGING_LIBDIR')
        sysdepdir = oe.path.join(staging_libdir, 'skalibs', 'sysdeps')
        opt = '--with-sysdeps=' + sysdepdir

    return opt


CONFIGUREOPTS = " --build=${BUILD_SYS} \
                  --target=${TARGET_SYS} \
                  --prefix=${prefix} \
                  --dynlibdir=${libdir} \
                  --libdir=${libdir} \
                  --includedir=${includedir} \
                  --sysconfdir=${sysconfdir} \
                  ${@get_sysdeps_opt(d)} \
                  --with-include=${STAGING_INCDIR} \
                  --with-lib=${STAGING_LIBDIR} \
                  --with-dynlib=${STAGING_LIBDIR} \
                  --enable-shared"

EXTRA_OECONF:append = " ${PACKAGECONFIG_CONFARGS}"

DISABLE_STATIC:append = " --disable-allstatic --disable-static-libc"

# Build functions
skarnet_do_configure() {
    if ! ${S}/configure ${CONFIGUREOPTS} ${EXTRA_OECONF} "$@"; then
        bbfatal_log "configure failed"
    fi

    # Remove build paths from generated <config.h>
    sed -i \
        -e "s%${RECIPE_SYSROOT_NATIVE}%%g" \
        -e "s%${RECIPE_SYSROOT}%%g" \
        -e "s%${SDKPATHNATIVE}%%g" \
        -e "s%${S}%.%g" \
        ${B}/src/include/${BPN}/config.h
}

skarnet_do_compile() {
    oe_runmake
}

skarnet_do_install() {
    oe_runmake 'DESTDIR=${D}' install
}

EXPORT_FUNCTIONS do_configure do_compile do_install

# No out-of-tree builds
B = "${S}"
