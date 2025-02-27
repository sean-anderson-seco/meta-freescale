# Copyright 2018 (C) O.S. Systems Software LTDA.
SUMMARY = "Samples for OpenGL ES"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=aeb969185a143c3c25130bc2c3ef9a50"
DEPENDS = "imx-gpu-viv zlib libpng procps"

SRC_URI = "git://source.codeaurora.org/external/imx/apitrace-imx.git;protocol=https;branch=imx_10.0 \
           file://0001-dlsym-workaround-glibc-2.34-build-failure.patch \
"
SRCREV = "90a59407f1cddd31b4dac67e5a2500100eb091bc"

S = "${WORKDIR}/git"

inherit cmake pkgconfig perlnative python3native

PACKAGECONFIG_BACKEND:mx6 = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'waffle', \
       bb.utils.contains('DISTRO_FEATURES',     'x11',    'x11', \
                                                             '', d), d)} \
"
PACKAGECONFIG_BACKEND:mx7 = "${PACKAGECONFIG_BACKEND:mx6}"
PACKAGECONFIG_BACKEND:mx8 = "waffle"

PACKAGECONFIG_GPU2D          = ""
PACKAGECONFIG_GPU2D:imxgpu2d = "vivante"

PACKAGECONFIG ??= " \
    egl \
    ${PACKAGECONFIG_BACKEND} \
    ${PACKAGECONFIG_GPU2D} \
"

PACKAGECONFIG[egl] = "-DENABLE_EGL=ON,-DENABLE_EGL=OFF,virtual/egl"
PACKAGECONFIG[gui] = "-DENABLE_GUI=ON,-DENABLE_GUI=OFF"
PACKAGECONFIG[multiarch] = "-DENABLE_MULTIARCH=ON,-DENABLE_MULTIARCH=OFF"
PACKAGECONFIG[waffle] = "-DENABLE_WAFFLE=ON,-DENABLE_WAFFLE=OFF,waffle"
PACKAGECONFIG[x11] = "-DENABLE_X11=ON,-DENABLE_X11=OFF"
PACKAGECONFIG[vivante] = "-DENABLE_VIVANTE=ON,-DENABLE_VIVANTE=OFF,virtual/libg2d"

SOLIBS = ".so"
FILES_SOLIBSDEV = ""
FILES:${PN} += " \
    ${libdir}/apitrace/scripts/* \
    ${libdir}/apitrace/wrappers/* \
"

PACKAGE_ARCH = "${MACHINE_SOCARCH}"
COMPATIBLE_MACHINE = "(imxgpu)"
