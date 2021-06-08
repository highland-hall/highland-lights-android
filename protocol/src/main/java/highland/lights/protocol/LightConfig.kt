package highland.lights.protocol

// @todo Idk if this is reasonable :/
data class LightConfig(val light_strips : Array<LightStrip>)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LightConfig

        if (!light_strips.contentEquals(other.light_strips)) return false

        return true
    }

    override fun hashCode(): Int {
        return light_strips.contentHashCode()
    }


}
