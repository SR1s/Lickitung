package me.sr1.lickitung.base;

public abstract class Singleton<Param, Type> {

    private volatile Type instance;

    public abstract Type create(Param param);

    public Type get(Param param) {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = create(param);
                }
            }
        }
        return instance;
    }
}