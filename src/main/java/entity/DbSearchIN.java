package entity;

public class DbSearchIN  {


    protected DbSearchIN.Countries countries;


    public DbSearchIN() {
        // Left black intentionally for default constructor.
    }

    public DbSearchIN.Countries getCountries() {
        return countries;
    }

    public void setCountries(final DbSearchIN.Countries value) {
        this.countries = value;
    }

    public static class Countries {

        protected String countryCode;
        protected String countryCode2;
        protected String countryCode3;

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(final String value) {
            this.countryCode = value;
        }

        public String getCountryCode2() {
            return countryCode2;
        }


        public void setCountryCode2(final String value) {
            this.countryCode2 = value;
        }

        public String getCountryCode3() {
            return countryCode3;
        }

        public void setCountryCode3(final String value) {
            this.countryCode3 = value;
        }

    }

}
