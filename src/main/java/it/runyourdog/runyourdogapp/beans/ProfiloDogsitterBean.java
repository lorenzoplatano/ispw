package it.runyourdog.runyourdogapp.beans;


public class ProfiloDogsitterBean extends ProfiloLavoratoreBean {
    private ProfiloDogsitterBean(Builder builder) {
        super(builder);
    }

    public static class Builder extends ProfiloLavoratoreBean.Builder<Builder> {
        @Override
        public ProfiloDogsitterBean build() {
            return new ProfiloDogsitterBean(this);
        }
    }
}
