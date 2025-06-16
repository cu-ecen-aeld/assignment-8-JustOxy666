#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-JustOxy666.git;protocol=ssh;branch=main \
           file://0001-Excluded-everything-except-for-scull.patch \
           file://init_scull \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "c59d481c6e708c57c77bebd334075beb98bd9886"

S = "${WORKDIR}/git"

# Startup
inherit module
inherit update-rc.d

FILES:${PN} += "${base_bindir}/scull_load"
FILES:${PN} += "${base_bindir}/scull_unload"
FILES:${PN} += "${sysconfdir}/init.d/init_scull"
# FILES_${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/"

# Refrence class which handles install scripts
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "init_scull"

# MODULES_INSTALL_TARGET = "modules"
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
# EXTRA_OEMAKE += "M=${S}/scull"
# EXTRA_OEMAKE += " EXTRA_CFLAGS='-I${S}/include'"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install:append () {
	
	install -d ${D}${base_bindir}
   install -d ${D}${sysconfdir}/init.d

	install -m 0755 ${S}/scull/scull_load ${D}${base_bindir}/
	install -m 0755 ${S}/scull/scull_unload ${D}${base_bindir}/
	install -m 0755 ${WORKDIR}/init_scull ${D}${sysconfdir}/init.d

	# install -m 0755 ${S}/scull/scull.ko ${D}${sysconfdir}/init.d
}