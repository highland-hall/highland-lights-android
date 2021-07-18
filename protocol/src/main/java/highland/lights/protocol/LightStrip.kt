package highland.lights.protocol

import com.igormaznitsa.jbbp.io.JBBPBitOutputStream
import com.igormaznitsa.jbbp.io.JBBPByteOrder
import java.io.IOException

data class LightStrip(var num_leds: Short, val ranges: ArrayList<LightRange>)
{
    @Throws(IOException::class)
    fun write(Out: JBBPBitOutputStream): LightStrip? {
        Out.writeShort(assrtExprNotNeg(num_leds.toInt()), JBBPByteOrder.LITTLE_ENDIAN)
        return this
    }

    private fun assrtExprNotNeg(value: Int): Int {
        require(value >= 0) { "Negative value in expression" }
        return value
    }
}
