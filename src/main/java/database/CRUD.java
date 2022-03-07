package database;

import java.util.ArrayList;

public interface CRUD<T, K> {
    public boolean create(T dados);
    public T read(K searchValue);
    public boolean update(T dados);
    public boolean delete(K id);
    public ArrayList<T> list();
}
