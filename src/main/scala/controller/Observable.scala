package controller

trait Observable:
  def add(observer: Observer): Unit
  def remove(observer: Observer): Unit
  def notifyObservers(): Unit
