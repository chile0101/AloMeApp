package com.thesis.alome.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thesis.alome.model.Address;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface AddressDao {
    @Insert(onConflict = REPLACE)
    void insertAddress(Address address);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceAddress(Address... addresses);

    @Update(onConflict = REPLACE)
    void updateAddress(Address address);

    @Delete
    void deleteAddress(Address address);

    @Query("DELETE FROM Address")
    void deleteAll();

    @Query("SELECT * FROM Address")
    public LiveData<List<Address>>  findAllAddressSysc();
}
