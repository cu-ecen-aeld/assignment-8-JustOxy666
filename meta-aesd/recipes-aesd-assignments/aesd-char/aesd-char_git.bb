#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-JustOxy666.git;protocol=ssh;branch=master \
           file://init_aesdchar \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "6663afb1a5b5a847bee02fd5914ed766d380f238"

S = "${WORKDIR}/git/aesd-char-driver"

# Startup
inherit module
inherit update-rc.d

FILES:${PN} += "${base_bindir}/aesdchar_load"
FILES:${PN} += "${base_bindir}/aesdchar_unload"
FILES:${PN} += "${sysconfdir}/init.d/init_aesdchar"

# Refrence class which handles install scripts
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "init_aesdchar"

# MODULES_INSTALL_TARGET = "modules"
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
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

	install -m 0755 ${S}/aesdchar_load ${D}${base_bindir}/
	install -m 0755 ${S}/aesdchar_unload ${D}${base_bindir}/
	install -m 0755 ${WORKDIR}/init_aesdchar ${D}${sysconfdir}/init.d

	# install -m 0755 ${S}/aesd-char-driver/aesd-char.ko ${D}${sysconfdir}/init.d
}