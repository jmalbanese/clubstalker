package com.evilkittyrpg.dataobjects;

import java.util.ArrayList;
import java.util.Collections;

import android.util.Log;

import com.evilkittyrpg.constants.Constants;
import com.evilkittyrpg.utils.Debug;

public  class Entities extends ArrayList<Entity>
{
	private static final long serialVersionUID = 2837076339111802452L;
	private String TAG = "Entities.java";

	public Entities()
	{}
	
	public synchronized void  clear()
	{
		super.clear();
	}

	
	public synchronized void addAll(Entities entities)
	{
		super.addAll(entities);
	}
	
	public Entity findByNameLegal(String q)
	{
		String key;
		String target;

		key = q.trim();
		key = key.toLowerCase();

		for (Entity e : this)
		{
			target = e.getName_legal().toLowerCase();
			if (target.contains(key))
			{
				return e;
			}
		}
		return (null);
	}

	public Entity findByUuid( String uuid )
	{
		for(Entity e : this)
		{
			if( e.getUuid().equals(uuid))
			{
				return(e);
			}
		}
		return null;
	}
	
	public Entity findByNameStage(String q)
	{
		String key;
		String target;

		key = q.trim();
		key = key.toLowerCase();

		for (Entity e : this)
		{
			target = e.getName_stage().toLowerCase();
			if (target.contains(key))
			{
				return e;
			}
		}
		return (null);
	}

	/**
	 * return all entities except for this one
	 * @param entity
	 * @return
	 */
	public Entities getAllExclude(Entity exclude)
	{
		Entities e = new Entities();
		e.addAll(this);
		e.remove(exclude);
		return e;
	}
	
		
	public Entities findByNameStageContains(String q)
	{
		String key;
		String target;
		key = q.trim();
		key = key.toLowerCase();
		Entities eList = new Entities();

		for (Entity e : this)
		{
			target = e.getName_stage().toLowerCase();

			if (target.contains(key))
			{
				eList.add(e);
			}
		}
		return (eList);
	}

	/**
	 * shuffle this list, force
	 */
	public void shuffleEntities()
	{
		Entities tlist = new Entities();
		
		Collections.shuffle(this);			// ok they are scrambled, now put the TOP_ENTITY values at the top of the list

		for( Entity e : this)
		{
			if(e.getDisplay_priority() >= Constants.TOP_ENTITY)
			{
				tlist.add(0, e);
			}
			else
			{
				tlist.add(e);
			}
		}
		clear();
		addAll(tlist);
	}

	public void destroy()
	{
		clear();
	}

	public Entities findByType(String key)
	{
		Entities es = new Entities();
		
		for(Entity e : this )
		{
			if( e.getType().equals(key))
			{
				es.add(e);
			}
		}
		return es;
	}
	
	public Entities findForPatronIndexActivity()
	{
		Entities es = new Entities();
		
		for(Entity e : this )
		{
			if( e.isShowItAll() || e.isThousandYardStare())
			{
				es.add(e);
			}
		}
		return es;
	}

	public Entities findForMapActivity()
	{
		Entities es = new Entities();
		
		for(Entity e : this )
		{
			if( e.isShowItAll() || e.isThousandYardStare() || e.isAnonymous())
			{
				// test for bad or not located patrons bad entries have 0.0 lat/lon
				if( e.isGoodFix() == true)
				{
					es.add(e);
				}
			}
		}
		return es;
	}

	// if true, override address enabled req
	
	public Entities findByType(String key, Boolean forceMappable)
	{
		Entities es = new Entities();
				
		for(Entity e : this )
		{
			if( e.getType().equals(key))
			{		
				if( forceMappable == true )
				{	
					es.add(e);
				}
				else
				{	
					if( e.getEnable_address().equals(Constants.ENABLED)) // ok to show on map
					{
						es.add(e);
					}
				}
			}
		}
		return es;
	}
	
	

	
	public void clearIntitiesNear()
	{
		for( Entity e : this)
		{
			e.setEntiesNear(0);
		}
	}
	
	public void read()
	{
		if(Debug.LOG)
		{
			Log.d(TAG, "Should not be called");
		}
	}
}
