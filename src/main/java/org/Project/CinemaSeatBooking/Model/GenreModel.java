package org.Project.CinemaSeatBooking.Model;

import lombok.Data;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
public class GenreModel {

    private String GenreId;

    private String Name;

    public GenreModel DTO(Document doc) {

        GenreModel genreModel = new GenreModel();
        genreModel.setGenreId(doc.getObjectId("_id").toString());
        genreModel.setName(doc.getString("Name"));

        return genreModel;
    }

}
