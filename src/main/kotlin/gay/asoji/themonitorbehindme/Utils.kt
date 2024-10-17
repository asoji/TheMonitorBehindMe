package gay.asoji.themonitorbehindme

fun String.isUrl(): Boolean {
    return this.matches("^(http(s):\\/\\/.)[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)$".toRegex())
}