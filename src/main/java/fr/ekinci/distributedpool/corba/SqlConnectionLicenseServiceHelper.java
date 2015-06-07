package fr.ekinci.distributedpool.corba;


/**
* fr/isidis/pw/impl/distributedpool/corba/SqlConnectionLicenseServiceHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from SqlConnectionLicenseService.idl
* dimanche 19 avril 2015 11 h 44 CEST
*/

abstract public class SqlConnectionLicenseServiceHelper
{
  private static String  _id = "IDL:fr/isidis/pw/impl/distributedpool/corba/SqlConnectionLicenseService:1.0";

  public static void insert (org.omg.CORBA.Any a, fr.ekinci.distributedpool.corba.SqlConnectionLicenseService that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static fr.ekinci.distributedpool.corba.SqlConnectionLicenseService extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (fr.ekinci.distributedpool.corba.SqlConnectionLicenseServiceHelper.id (), "SqlConnectionLicenseService");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static fr.ekinci.distributedpool.corba.SqlConnectionLicenseService read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_SqlConnectionLicenseServiceStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, fr.ekinci.distributedpool.corba.SqlConnectionLicenseService value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static fr.ekinci.distributedpool.corba.SqlConnectionLicenseService narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof fr.ekinci.distributedpool.corba.SqlConnectionLicenseService)
      return (fr.ekinci.distributedpool.corba.SqlConnectionLicenseService)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      fr.ekinci.distributedpool.corba._SqlConnectionLicenseServiceStub stub = new fr.ekinci.distributedpool.corba._SqlConnectionLicenseServiceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static fr.ekinci.distributedpool.corba.SqlConnectionLicenseService unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof fr.ekinci.distributedpool.corba.SqlConnectionLicenseService)
      return (fr.ekinci.distributedpool.corba.SqlConnectionLicenseService)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      fr.ekinci.distributedpool.corba._SqlConnectionLicenseServiceStub stub = new fr.ekinci.distributedpool.corba._SqlConnectionLicenseServiceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}