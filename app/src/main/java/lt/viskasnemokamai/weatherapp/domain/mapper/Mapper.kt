package lt.viskasnemokamai.weatherapp.domain.mapper


/**
 * Created by Greta Radisauskaite on 09/10/2020.
 */
interface Mapper<Source, Target> {

    fun map(source: Source): Target

}