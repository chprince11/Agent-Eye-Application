package ca.sheridancollege.prince.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import ca.sheridancollege.prince.beans.Mission;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@Component
public class DatabaseAccess 
{
	private NamedParameterJdbcTemplate jdbc;

	public DatabaseAccess(NamedParameterJdbcTemplate jdbc)
	{
		this.jdbc=jdbc;
	}
	
	public List<Mission> getMissionList(Mission obj)
	{
		MapSqlParameterSource namedParameter=new MapSqlParameterSource();
		String s1="SELECT * FROM missions WHERE agent=:agent;";
		System.out.println(obj.getAgent());
		namedParameter.addValue("agent",obj.getAgent());
		ArrayList<Mission> missionlst=(ArrayList<Mission>)jdbc.query(s1, namedParameter, new BeanPropertyRowMapper<Mission>(Mission.class));
		
		return missionlst;
	}
	
	public int createMission(Mission obj) 
	{
		MapSqlParameterSource namedParameter=new MapSqlParameterSource();
		String s = "INSERT INTO missions (title, gadget1, gadget2, agent) VALUES (:title, :gadget1, :gadget2, :agent)";

		namedParameter.addValue("title",obj.getTitle());
		namedParameter.addValue("gadget1",obj.getGadget1());
		namedParameter.addValue("gadget2",obj.getGadget2());
		namedParameter.addValue("agent", obj.getAgent());
		
		int result=jdbc.update(s, namedParameter);
		return result;
		
	}
	
	public Mission deletemissionById(Long id) {
	    MapSqlParameterSource namedParameter = new MapSqlParameterSource();
	    String s4 = "SELECT * FROM missions WHERE id=:id;";
	    namedParameter.addValue("id", id);
	    BeanPropertyRowMapper<Mission> mapper = new BeanPropertyRowMapper<>(Mission.class);
	    Mission mission = null;

	    try {
	        mission = jdbc.queryForObject(s4, namedParameter, mapper);
	        // Print mission details or perform any necessary operations before deletion

	        String sp = "DELETE FROM missions WHERE id=:id;";
	        int r = jdbc.update(sp, namedParameter);
	        if (r > 0) {
	            System.out.println("Deleted mission " + id + " from table");
	        }
	    } catch (EmptyResultDataAccessException e) {
	        System.out.println("Agent not found for id=" + id);
	    }

	    return mission;
	}
	
	public int updateMission1(Mission mission)
	{
		MapSqlParameterSource namedParameter=new MapSqlParameterSource();
		String s3="UPDATE missions set title=:title,gadget1=:gadget1,gadget2=:gadget2,agent=:agent WHERE id=:id;";
		namedParameter.addValue("title",mission.getTitle());
		namedParameter.addValue("gadget1",mission.getGadget1());
		namedParameter.addValue("gadget2",mission.getGadget2());
		namedParameter.addValue("agent", mission.getAgent());
		namedParameter.addValue("id",mission.getId());
		
		int rvalue=jdbc.update(s3, namedParameter);
		
		return rvalue;
	}
	
	@SuppressWarnings("null")
	public Mission getMission(Long id)
	{
		MapSqlParameterSource namedParameter=new MapSqlParameterSource();
		String s4="SELECT * FROM missions WHERE id=:id;";
		namedParameter.addValue("id", id);
		BeanPropertyRowMapper<Mission> mapper=new BeanPropertyRowMapper<>(Mission.class);
		Mission mission= null;
		
		try
		{
			mission = jdbc.queryForObject(s4,namedParameter,mapper);
			System.out.println(mission.getAgent());
		}
		catch(EmptyResultDataAccessException e)
		{
			System.out.println("Agent not found for id="+id);
			
		}
		return mission ;

	}
	
	
	
	}