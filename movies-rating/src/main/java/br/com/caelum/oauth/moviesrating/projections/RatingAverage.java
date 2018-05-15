package br.com.caelum.oauth.moviesrating.projections;

import java.math.BigDecimal;

public interface RatingAverage {

    String getMovie();

    BigDecimal getAverage();
}
