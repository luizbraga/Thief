package edu.cuny.lehman.elements;

public class DoorActivator
{
   boolean isPressed;

  public DoorActivator()
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