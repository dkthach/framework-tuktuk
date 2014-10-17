package com.mg.studio.alice.myframework.demotest;

import android.os.Bundle;

import com.mg.studio.alice.myframework.director.CanvasGame;
import com.mg.studio.alice.myframework.director.MGDialogManager;
import com.mg.studio.alice.myframework.director.MGDirector;
import com.mg.studio.engine.MGCanvas;
import com.mg.studio.engine.MGStandardGameActivity;

/**
 * @author Dk Thach
 * 
 */
public class MainActivity extends MGStandardGameActivity 
{

   
    private CanvasGame                 canvasGame;
 
   

    @Override
    public MGCanvas getCanvas()
    {
        if (canvasGame == null)
        {
            canvasGame = new CanvasGame(this);
        }
        return canvasGame;
    }

    @Override
    public void gameCreate(Bundle savedInstanceState)
    {
        canvasGame = new CanvasGame(this);
        RM.loadrs();
    }

   

    @Override
    public void gamePause()
    {
      

    }

    @Override
    public void gameResume()
    {
      

    }

    @Override
    public void onBackPressed()
    {
        if (MGDialogManager.shareManager().onBackPressed())
        {
            return;
        }
        if (MGDirector.shareDirector().onBackPressed())
        {
            return;
        }

       //show dialog quit game ở đây
    }

    @Override
    protected boolean isBackTwiceToExit()
    {
        return false;
    }

    @Override
    public void gameDestroy()
    {
      

    }

    @Override
    public void gameStop()
    {
      
    }

    @Override
    public void gameStart()
    {

    }

@Override
public String getGameCode() {
	// TODO Auto-generated method stub
	return null;
}

   

  

   

  
     

}
