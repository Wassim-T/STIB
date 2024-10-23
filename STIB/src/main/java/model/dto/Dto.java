package model.dto;

import java.util.Objects;

public class Dto<K> {
    protected K key;


    protected Dto(K key){
        if (key == null){
            throw new IllegalArgumentException("No key : "+key);
        }
        this.key = key;
    }

    protected Dto(){
        this.key = null;
    }


    public K getKey(){
        return key;
    }

    public void setKey(K key){
        this.key = key;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.key);
        return hash;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dto<?> other = (Dto<?>) obj;
        return Objects.equals(this.key, other.key);

    }
}
