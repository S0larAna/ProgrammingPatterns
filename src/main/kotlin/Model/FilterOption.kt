package Model

enum class FilterOption(val text: String) {
    YES("Да"),
    NO("Нет"),
    NO_MATTER("Не важно");

    companion object {
        fun fromText(text: String): FilterOption {
            return values().first { it.text == text }
        }
    }
}