package highland.lights.protocol

import com.igormaznitsa.jbbp.io.JBBPBitOutputStream
import highland.lights.protocol.messages.Header
import highland.lights.protocol.messages.NetworkConfig
import java.io.ByteArrayOutputStream
import java.net.InetAddress
import java.net.Socket

//@todo static enum for message types
class LightInterface() {
    fun sendWifiConfig(config_ip : InetAddress, config_port : Int, ssid : String, pass : String) : Unit
    {
        var config_socket : Socket = Socket(config_ip, config_port);
        var message_stream : JBBPBitOutputStream = JBBPBitOutputStream(config_socket.getOutputStream());
        var header : Header = Header();
        // This is cursed pls no
        header.version = 0.toChar();
        header.type = 0x02.toChar();
        var networkConfig : NetworkConfig = NetworkConfig();
        networkConfig.ssid_len = ssid.length.toChar();
        networkConfig.ssid = ssid.encodeToByteArray();
        networkConfig.pass_len = ssid.length.toChar();
        networkConfig.pass = pass.encodeToByteArray();
        header.write(message_stream);
        networkConfig.write(message_stream);
        config_socket.close();
    }

    fun sendLightConfig(config_ip: InetAddress, config_port: Int, light_config : LightConfig) : Unit
    {
        var config_socket : Socket = Socket(config_ip, config_port);
        var message_stream : JBBPBitOutputStream = JBBPBitOutputStream(config_socket.getOutputStream());
        // @todo this likely should be a static member of Header!
        var add_strip_header : Header = Header();
        add_strip_header.version = 0.toChar();
        add_strip_header.type = 0x04.toChar();
        var add_range_header : Header = Header();
        add_range_header.version = 0.toChar();
        add_range_header.type = 0x07.toChar();
        for((index,strip) in light_config.light_strips.withIndex())
        {
            add_strip_header.write(message_stream)
            message_stream.write(index) // @todo figure out if this does the write thing
            strip.write(message_stream)
            for(range in strip.ranges)
            {
                add_range_header.write(message_stream)
                range.write(message_stream)
            }
        }
    }
}