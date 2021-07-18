package highland.lights.protocol

import com.igormaznitsa.jbbp.io.JBBPBitOutputStream
import highland.lights.protocol.messages.Header
import highland.lights.protocol.messages.NetworkConfig
import highland.lights.protocol.messages.TestRange
import highland.lights.protocol.messages.TestStrip
import java.net.InetAddress
import java.net.Socket


//@todo static enum for message types
class LightInterface() {
    fun sendWifiConfig(config_ip: InetAddress, config_port: Int, ssid: String, pass: String) : Unit
    {

        // This is cursed pls no
        val thread = Thread {
            try {
                var config_socket : Socket = Socket(config_ip, config_port);
                var message_stream : JBBPBitOutputStream = JBBPBitOutputStream(config_socket.getOutputStream());
                var header : Header = Header();
                header.version = 0.toChar();
                header.type = 0x02.toChar();
                var networkConfig : NetworkConfig = NetworkConfig();
                networkConfig.ssid_len = ssid.length.toChar();
                networkConfig.ssid = ssid.encodeToByteArray();
                networkConfig.pass_len = pass.length.toChar();
                networkConfig.pass = pass.encodeToByteArray();
                header.write(message_stream);
                networkConfig.write(message_stream);
                //config_socket.close();
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()

    }

    fun sendLightConfig(config_ip: InetAddress, config_port: Int, light_config: LightConfig) : Unit
    {
        val thread = Thread {
            var config_socket : Socket = Socket(config_ip, config_port);
            var message_stream : JBBPBitOutputStream = JBBPBitOutputStream(config_socket.getOutputStream());
            // @todo this likely should be a static member of Header!
            var add_strip_header : Header = Header()
            add_strip_header.version = 0.toChar()
            add_strip_header.type = 0x04.toChar()
            var add_range_header : Header = Header()
            add_range_header.version = 0.toChar()
            add_range_header.type = 0x07.toChar()
            var test_strip_header : Header = Header()
            test_strip_header.version = 0.toChar()
            test_strip_header.type = 0x03.toChar()
            for((index, strip) in light_config.light_strips.withIndex())
            {
                if(strip != null) {
                    add_strip_header.write(message_stream)
                    message_stream.write(index) // @todo figure out if this does the write thing
                    strip.write(message_stream)
                    for ((ridx,range) in strip.ranges.withIndex()) {
                        add_range_header.write(message_stream)
                        range.write(message_stream)
                    }
                    test_strip_header.write(message_stream)
                    var test_strip = TestStrip()
                    test_strip.strip_idx = index.toChar()
                    test_strip.write(message_stream)
                }
            }
        }
        thread.start()
    }
}