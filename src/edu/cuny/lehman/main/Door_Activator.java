package edu.cuny.lehman.main;

public class Door_Activator
{
   boolean isPressed;

  public Door_Activator()
  {

  }

  public void pressed()
  {
      isPressed = true;
      
  }

  public void released()
  {
    isPressed = false;
  }

} 