#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-JustOxy666.git;protocol=ssh;branch=main \
           file://0001-Only-using-misc-modules.patch \
           file://init_misc-modules \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "c59d481c6e708c57c77bebd334075beb98bd9886"

S = "${WORKDIR}/git"

# Startup
inherit module
inherit update-rc.d

FILES:${PN} += "${base_bindir}/misc-modules_load"
FILES:${PN} += "${base_bindir}/misc-modules_unload"
FILES:${PN} += "${sysconfdir}/init.d/init_misc-modules"

# Refrence class which handles install scripts
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "init_misc-modules"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install:append () {
   install -d ${D}${base_bindir}
   install -d ${D}${sysconfdir}/init.d

	install -m 0755 ${S}/misc-modules/module_load ${D}${base_bindir}/misc-modules_load
	install -m 0755 ${S}/misc-modules/module_unload ${D}${base_bindir}/misc-modules_unload
	install -m 0755 ${WORKDIR}/init_misc-modules ${D}${sysconfdir}/init.d
}
