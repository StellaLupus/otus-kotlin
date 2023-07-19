open class Rectangle(open val width: Int, val height: Int): Figure() {
    override fun area() = width * height

    override fun toString(): String {
        return "Rectangle(${width}x$height)"
    }

    override fun equals(other: Any?): Boolean {
        return if(other is Rectangle) width == other.width && height == other.height else false
    }

    override fun hashCode(): Int {
        return (width + height).hashCode()
    }
}