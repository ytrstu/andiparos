PROGRAM_NAME="Andiparos"
PROGRAM_VERSION="1.0"
hdiutil create ${PROGRAM_NAME}-v${PROGRAM_VERSION}.dmg -volname "${PROGRAM_NAME}-v${PROGRAM_VERSION}" -fs HFS+ -srcfolder "./${PROGRAM_NAME}-dmg/"
