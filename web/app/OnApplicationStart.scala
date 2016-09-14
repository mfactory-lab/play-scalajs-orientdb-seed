import javax.inject.Inject

import services.database.bootstrap.BootstrapDB

class OnApplicationStart @Inject()(bootstrap: BootstrapDB) {

  bootstrap.run()

}