package highland.lights.protocol

import com.igormaznitsa.jbbp.io.JBBPBitOutputStream
import com.igormaznitsa.jbbp.io.JBBPByteOrder
import java.io.IOException

data class LightRange(val strip_idx: Char, val start_idx : Short, val end_idx : Short)
{
    @Throws(IOException::class)
    fun write(Out: JBBPBitOutputStream): LightRange? {
        Out.write(assrtExprNotNeg(strip_idx.toInt()))
        Out.writeShort(assrtExprNotNeg(start_idx.toInt()), JBBPByteOrder.LITTLE_ENDIAN)
        Out.writeShort(assrtExprNotNeg(start_idx.toInt()), JBBPByteOrder.LITTLE_ENDIAN)
        return this
    }

    private fun assrtExprNotNeg(value: Int): Int {
        require(value >= 0) { "Negative value in expression" }
        return value
    }
}
