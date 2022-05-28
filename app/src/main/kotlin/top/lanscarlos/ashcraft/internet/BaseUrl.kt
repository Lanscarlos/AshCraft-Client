package top.lanscarlos.ashcraft.internet

object BaseUrl {

    // 本地
    val LOCALHOST = "http://172.17.32.120:8080/"

    // Lanscarlos
    val LANSCARLOS = "http://lanscarlos.top:1226/"

    // 阿里 CDN
    val ALI_CDN = "https://gw.alicdn.com/tfs/"

    // 京东 CPS
    val JD_CPS_IMG = "https://imgcps.jd.com/"

    val BY_IMG = "https://img10.360buyimg.com/"

    val values = listOf(
        LOCALHOST,
        ALI_CDN,
        JD_CPS_IMG,
        BY_IMG
    )
}