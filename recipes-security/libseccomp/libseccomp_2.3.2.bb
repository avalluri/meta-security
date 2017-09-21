SUMMARY = "interface to seccomp filtering mechanism"
DESCRIPTION = "The libseccomp library provides and easy to use, platform independent,interface to the Linux Kernel's syscall filtering mechanism: seccomp."
SECTION = "security"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE;beginline=0;endline=1;md5=8eac08d22113880357ceb8e7c37f989f"

SRCREV = "2331d104bc0cbde5f6c54e504a038e52bfe8e12d"

PV = "2.3.2+git${SRCPV}"

SRC_URI = "git://github.com/seccomp/libseccomp.git;branch=release-2.3"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig

PACKAGECONFIG ??= ""
PACKAGECONFIG[python] = "--enable-python, --disable-python, python"

do_compile_append() {
    oe_runmake -C tests check-build
}

do_install_append() {
    install -d ${D}/${libdir}/${PN}/tests
    install -d ${D}/${libdir}/${PN}/tools
    for file in $(find tests/* -executable -type f); do
        install -m 744 ${S}/${file} ${D}/${libdir}/${PN}/tests
    done
    for file in $(find tests/*.tests -type f); do
        install -m 744 ${S}/${file} ${D}/${libdir}/${PN}/tests
    done
    for file in $(find tools/* -executable -type f); do
        install -m 744 ${S}/${file} ${D}/${libdir}/${PN}/tools
    done
}

PACKAGES += " ${PN}-tests"
FILES_${PN} = "${bindir} ${libdir}/${PN}.so*"
FILES_${PN}-tests = "${libdir}/${PN}/tools ${libdir}/${PN}/tests"
FILES_${PN}-dbg += "${libdir}/${PN}/tests/.debug/* ${libdir}/${PN}/tools/.debug"

RDEPENDS_${PN} = "bash"
RDEPENDS_${PN}-tests = "bash"
