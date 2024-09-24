package convenience;

public interface Message {


    static  String informationReceived(String name) {
        return String.format("Assalomu aleykum hurmatli %s sizning arizangiz qabul qilindi biz sizga tez orada aloqaga chiqamiz kiritilgan malumotlar tekshiruvga yuborildi bizni kanpaniyani tanlagniz uchun rahmat ",name);
    }

    static String removeCard(String name) {
        return String.format("Assalomu aleykum hurmatli %s sizning arizangizda malumotlar yetarsiz ekanligi aniqlandi iltimos qaytadan urinib ko'ring ",name);
    }
}
