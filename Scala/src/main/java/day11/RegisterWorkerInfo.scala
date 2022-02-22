package day11

// worker注册信息
case class RegisterWorkerInfo(id: String,cpu: Int, ram: Int)

// 扩展worker
class WorkerInfo(id: String,cpu: Int, ram: Int)

//注册成功后，返回的对象
case object RegisterWorkerInfo
