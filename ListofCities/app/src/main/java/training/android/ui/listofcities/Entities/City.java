package training.android.ui.listofcities.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by guedi on 7/16/2017.
 */

@Entity
public class City {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    @Index(unique = true)
    private String name;

    @Generated(hash = 1097063181)
    public City(Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 750791287)
    public City() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
